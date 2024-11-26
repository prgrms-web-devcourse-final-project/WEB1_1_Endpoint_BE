variable "db_username" {
  description = "RDS 데이터베이스의 사용자 이름"
  type        = string
}

variable "db_password" {
  description = "RDS 데이터베이스의 비밀번호"
  type        = string
  sensitive   = true
}