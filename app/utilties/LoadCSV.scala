package utilties

import java.io.File

object LoadCSV {

  def getFilesList: List[String] = {
    new File("/home/synerzip/software/neo4j-community-3.0.2/import/MyDatabase").list().toList
  }
  def operateOnDb(operationToDo: String) = operationToDo match {
    //case "Create" => getFilesList.foreach { files => GraphDBConfig.runQuery("LOAD CSV FROM \"file:///MyDatabase/" + files.trim + "\" AS row match(from:Location),(to:Location) where (from.locCode=row[1] and to.locCode=row[2]) create (from)-[rel:FlyingTo{name:row[0],duration:row[5],depLoc:row[1],arrLoc:row[2],time:row[4],date:row[3],fare:row[6]}]->(to)") }
    case "Update" => {
      getFilesList.foreach { files => GraphDBConfig.runQuery("LOAD CSV FROM \"file:///MyDatabase/" + files.trim
        + "\" AS row MATCH ()-[r:FlyingTo{name:row[0]}]->() DELETE r") }
      getFilesList.foreach { files => GraphDBConfig.runQuery("LOAD CSV FROM \"file:///MyDatabase/" + files.trim
        + "\" AS row MATCH (from:Location),(to:Location) WHERE (from.locCode=row[1] and to.locCode=row[2]) CREATE (from)-[rel:FlyingTo{name:row[0],duration:row[5],depLoc:row[1],arrLoc:row[2],time:row[4],date:row[3],fare:row[6]}]->(to)") }
                      }
    case "Delete" => getFilesList.foreach { files => GraphDBConfig.runQuery("LOAD CSV FROM \"file:///MyDatabase/" + files.trim + "\" AS row MATCH p=()-[r:FlyingTo{name:row[0]}]->() delete r") }
    case _ => throw new Exception("No such Operation to Perform!")
  }
}
