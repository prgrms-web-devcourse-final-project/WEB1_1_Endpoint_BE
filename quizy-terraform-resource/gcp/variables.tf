variable "project_id" {
  description = "The project ID to host the cluster in"
  type        = string
}

variable "cluster_name" {
  description = "The name for the GKE cluster"
  type        = string
}

variable "db_password" {
  description = "Database password"
  type        = string
  sensitive   = true
}


variable "zones" {
  description = "GKE 클러스터 영역"
  type        = list(string)
}

variable "region" {
  description = "The region to host the cluster in"
  type        = string
}

variable "network" {
  description = "The VPC network name"
  type        = string
}

variable "env_name" {
  description = "The environment name"
  type        = string
}

variable "ip_range_pods_name" {
  description = "The secondary ip range name for pods"
  type        = string
}

variable "ip_range_services_name" {
  description = "The secondary ip range name for services"
  type        = string
}
