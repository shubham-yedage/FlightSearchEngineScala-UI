package utilties

import org.neo4j.driver.v1._

object GraphDBConfig {
  def runQuery(query:String):StatementResult={
    def getSession:Session = {
      val driver: Driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "shubham5252"))
      driver.session
    }
    getSession.run(query)
  }

}
