rootProject.name = "quizy"

include("quiz-service")
include("quiz-service:quiz-infra")
include("quiz-service:quiz-domain")
include("quiz-service:quiz-application")
include("quiz-service:quiz-application:app-api")

include("user-service")
include("user-service:user-infra")
include("user-service:user-domain")
include("user-service:user-application")
include("user-service:user-application:app-api")