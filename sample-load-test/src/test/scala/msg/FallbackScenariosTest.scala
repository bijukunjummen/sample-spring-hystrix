package msg

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class FallbackScenariosTest extends Simulation {

  val httpConf = http.baseURL("http://192.168.122.130:8080/sample-hystrix-aggregate")

  val headers = Map("Accept" -> """application/json""")

  val scn = scenario("Get the Fallback Controller")
    .exec(http("Fallback Command")
    .get("/fallback"))

  setUp(scn.inject(rampUsers(50) over (1 seconds)).protocols(httpConf))
}
