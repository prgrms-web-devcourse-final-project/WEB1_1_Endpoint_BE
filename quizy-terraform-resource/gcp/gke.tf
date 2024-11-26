# GKE 클러스터 생성
module "gke" {
  source                     = "terraform-google-modules/kubernetes-engine/google//modules/private-cluster"
  version                    = "~> 25.0"
  project_id                 = var.project_id
  name                       = var.cluster_name
  region                     = var.region
  zones                      = var.zones
  network                    = module.vpc.network_name
  subnetwork                 = module.vpc.subnets_names[0]
  ip_range_pods              = var.ip_range_pods_name
  ip_range_services          = var.ip_range_services_name
  http_load_balancing        = true
  network_policy             = true
  horizontal_pod_autoscaling = true
  enable_private_endpoint    = false
  enable_private_nodes       = true
  monitoring_service = "none"
  logging_service    = "none"
  master_ipv4_cidr_block     = "10.0.0.0/28"

  node_pools = [
    {
      name            = "default-node-pool"
      machine_type    = "e2-standard-2"
      node_locations  = "asia-northeast3-a,asia-northeast3-b,asia-northeast3-c"
      min_count       = 1
      max_count       = 1
      disk_size_gb    = 30
      spot            = false
      image_type      = "COS_CONTAINERD"
      disk_type       = "pd-standard"
      logging_variant = "DEFAULT"
      auto_repair     = true
      auto_upgrade    = true
      service_account = "rkdwldnjs149@${var.project_id}.iam.gserviceaccount.com"
    }
  ]

  node_pools_oauth_scopes = {
    all = [
      "https://www.googleapis.com/auth/logging.write",
      "https://www.googleapis.com/auth/monitoring",
      "https://www.googleapis.com/auth/trace.append",
      "https://www.googleapis.com/auth/service.management.readonly",
      "https://www.googleapis.com/auth/devstorage.read_only",
      "https://www.googleapis.com/auth/servicecontrol"
    ]
  }

  node_pools_labels = {
    all = {}
    default-node-pool = {
      default-node-pool = true
    }
  }

  node_pools_metadata = {
    all = {}
    default-node-pool = {
      #노드가 종료될 때, kubectl drain 명령어를 사용하여
      # 파드를 안전하게 제거하고 필요하다면 다른 노드로 재스케줄링 되도록 하는 스크립트를 실행한다.
      shutdown-script                 = "kubectl --kubeconfig=/var/lib/kubelet/kubeconfig drain --force=true --ignore-daemonsets=true --delete-local-data \"$HOSTNAME\""
      node-pool-metadata-custom-value = "default-node-pool"
    }
  }

  node_pools_taints = {
    all = []

    default-node-pool = [
      {
        key    = "default-node-pool"
        value  = true
        effect = "PREFER_NO_SCHEDULE"
      }
    ]
  }

  node_pools_tags = {
    all = []

    default-node-pool = [
      "default-node-pool",
    ]
  }

  depends_on = [module.vpc, google_service_account.default]
}

# GKE 클러스터 인증 설정
module "gke_auth" {
  source               = "terraform-google-modules/kubernetes-engine/google//modules/auth"
  version              = "29.0.0"
  project_id           = var.project_id
  location             = module.gke.location
  cluster_name         = module.gke.name
  use_private_endpoint = true
  depends_on           = [module.gke]
  # module.gke에 의존한다. 즉 gke에 설정된 클러스터가 생성된 후 인증 정보가 설정된다.
}

# 로컬에 kubeconfig 저장
resource "local_file" "kubeconfig" {
  content  = module.gke_auth.kubeconfig_raw
  filename = "kubeconfig-${var.env_name}"
}

# GKE 노드용 서비스 계정 생성 및 IAM 역할 부여
resource "google_service_account" "default" {
  account_id   = "rkdwldnjs149"
  project      = var.project_id
  display_name = "K8s Node Service Account"
}

# Logging Writer 역할 부여
resource "google_project_iam_member" "sa_logging" {
  project = var.project_id
  role    = "roles/logging.logWriter"
  member  = "serviceAccount:${google_service_account.default.email}"
}

# Monitoring Metric Writer 역할 부여
resource "google_project_iam_member" "sa_monitoring" {
  project = var.project_id
  role    = "roles/monitoring.metricWriter"
  member  = "serviceAccount:${google_service_account.default.email}"
}

# Compute Security Admin 역할 부여
resource "google_project_iam_member" "sa_compute_security_admin" {
  project = var.project_id
  role    = "roles/compute.securityAdmin"
  member  = "serviceAccount:${google_service_account.default.email}"
}

