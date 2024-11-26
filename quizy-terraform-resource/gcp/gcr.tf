resource "google_artifact_registry_repository" "my-repo" {
  location      = "asia-northeast3"
  repository_id = "quizy"
  description   = "example docker repository"
  format        = "DOCKER"

  docker_config {
    immutable_tags = true
  }
}

resource "google_service_account" "github_actions" {
  account_id   = "github-actions-sa"  # 서비스 계정 ID (원하는 대로 설정 가능)
  project      = var.project_id       # 프로젝트 ID 변수
  display_name = "GitHub Actions Service Account"
}

resource "google_project_iam_member" "github_actions_artifact_registry_writer" {
  project = var.project_id
  role    = "roles/artifactregistry.writer"
  member  = "serviceAccount:${google_service_account.github_actions.email}"
}

# Artifact Registry Reader 역할 부여
resource "google_project_iam_member" "sa_artifact_registry_reader" {
  project = var.project_id
  role    = "roles/artifactregistry.reader"
  member  = "serviceAccount:${google_service_account.default.email}"
}
