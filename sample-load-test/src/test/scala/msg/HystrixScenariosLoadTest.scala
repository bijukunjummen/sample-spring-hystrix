package msg

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class HystrixScenariosLoadTest extends Simulation {

  val httpConf = http.baseURL("http://192.168.122.130:8080/sample-hystrix-aggregate")

  val headers = Map("Accept" -> """application/json""")

  val scn = scenario("Get the Hello Command Controller")
    .exec(http("Hello Command")
    .get("/hello")
    .queryParam("greeting", "World"))

  setUp(scn.inject(rampUsers(50) over (1 seconds)).protocols(httpConf))
}
