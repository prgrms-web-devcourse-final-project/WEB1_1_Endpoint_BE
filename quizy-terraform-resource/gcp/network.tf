# VPC 네트워크 생성
module "vpc" {
  source       = "terraform-google-modules/network/google"
  version      = "~> 8.0"
  project_id   = var.project_id
  network_name = "${var.network}-${var.env_name}"
  routing_mode = "GLOBAL"

  subnets = [
    {
      subnet_name           = "subnet-01"
      subnet_ip             = "10.10.20.0/24"
      subnet_region         = var.region
      subnet_private_access = true
      subnet_flow_logs      = true
      description           = "Private subnet for GKE cluster"
    }
  ]

  secondary_ranges = {
    subnet-01 = [
      {
        range_name    = var.ip_range_pods_name
        ip_cidr_range = "10.20.0.0/16"
      },
      {
        range_name    = var.ip_range_services_name
        ip_cidr_range = "10.21.0.0/16"
      }
    ]
  }
}

resource "google_compute_global_address" "gateway_ip" {
  name = "gateway-ip"
}

resource "google_compute_global_address" "argo_ip" {
  name = "argo-ip"
}

resource "google_compute_global_address" "kafka_ui_ip" {
  name = "kafka-ui-ip"
}

# Cloud Router 생성
resource "google_compute_router" "router" {
  name    = "nat-router"
  network = module.vpc.network_name
  region  = var.region
}

# Cloud NAT 구성
resource "google_compute_router_nat" "nat" {
  name                               = "nat-config"
  router                             = google_compute_router.router.name
  region                             = google_compute_router.router.region
  nat_ip_allocate_option            = "AUTO_ONLY"
  source_subnetwork_ip_ranges_to_nat = "ALL_SUBNETWORKS_ALL_IP_RANGES"
}