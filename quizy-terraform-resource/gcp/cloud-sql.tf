# Cloud SQL 인스턴스를 VPC 네트워크에 연결하기 위한 VPC 피어링 구성
resource "google_compute_global_address" "private_ip_address" {
  name          = "private-ip-address"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = module.vpc.network_name

  depends_on = [google_project_service.services]
}

resource "google_service_networking_connection" "private_vpc_connection" {
  network                 = module.vpc.network_name
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.private_ip_address.name]

  depends_on = [
    google_project_iam_member.sa_compute_security_admin,
    google_project_service.services,
    google_compute_global_address.private_ip_address
  ]
}

resource "google_project_service" "services" {
  for_each = toset([
    "servicenetworking.googleapis.com",
    "sqladmin.googleapis.com",
    "compute.googleapis.com"
  ])

  project = var.project_id
  service = each.key

  disable_on_destroy = false
}



# Cloud SQL 인스턴스 생성
resource "google_sql_database_instance" "instance" {
  name             = "quizy-db-instance"
  region           = var.region
  database_version = "MYSQL_8_0"

  depends_on = [google_service_networking_connection.private_vpc_connection]

  settings {
    tier = "db-f1-micro"

    ip_configuration {
      ipv4_enabled        = true
      private_network     = module.vpc.network_self_link
      require_ssl         = false


      authorized_networks {
        name  = "my-ip"
        value = "112.186.245.109/32"
      }
    }
  }

  deletion_protection = false
}

# 데이터베이스 생성
resource "google_sql_database" "database" {
  name     = "quiz"
  instance = google_sql_database_instance.instance.name
}

# 데이터베이스 사용자 생성
resource "google_sql_user" "users" {
  name     = "mysqluser"
  instance = google_sql_database_instance.instance.name
  host = "%"
  password = var.db_password
}

# Output 정의
output "private_ip" {
  value = google_sql_database_instance.instance.private_ip_address
}

output "public_ip" {
  value = google_sql_database_instance.instance.public_ip_address
}