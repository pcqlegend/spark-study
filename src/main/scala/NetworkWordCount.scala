import org.apache.spark.SparkConf

/**
  * Created by pcq on 2018/9/17.
  */
object NetworkWordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[2]")
    val ssc = new StringContext(sparkConf,Seconds(10))
    val

  }

}
