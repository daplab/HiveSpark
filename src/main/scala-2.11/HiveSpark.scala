/**
 * Created by cbovigny on 10/23/15.
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.hive.HiveContext

/* Run me:
 * spark-submit --class "HiveSpark" --master yarn-client hivespark_2.11-1.0.jar
 */
object HiveSpark {

  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("HiveFromSpark")
    val sc = new SparkContext(sparkConf)

    // A hive context adds support for finding tables in the MetaStore and writing queries
    // using HiveQL. Users who do not have an existing Hive deployment can still create a
    // HiveContext. When not configured by the hive-site.xml, the context automatically
    // creates metastore_db and warehouse in the current directory.

    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._
    import hiveContext.sql

    // 1. Get the count number of BXDataset
    val count = sql("SELECT COUNT(*) FROM BXDataSet").collect().head.getLong(0)
    println(s"COUNT(*): $count")

    // 2. Here you have to complete the query :
    val yearCount = sql("from bxdataset SELECT yearofpublication, count(booktitle) group by yearofpublication order by yearofpublication").collect().foreach(println)


    sc.stop()
  }
}
