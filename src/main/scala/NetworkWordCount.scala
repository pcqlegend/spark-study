import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by pcq on 2018/9/17.
  */
object NetworkWordCount {
  def main(args: Array[String]): Unit = {
    val (host, port) = if (args.length == 0) {
      ("localhost", "9999")
    } else {
      val host = args(0)
      val port = args(1)
      (host, port)
    }

    val sparkConf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lines = ssc.socketTextStream(host, port.toInt, StorageLevel.MEMORY_AND_DISK);
    //### batch1
    // hello boy
    // hello girl
    // hello spark
    //### batch2
    //hello cat
    //hello dog
    //hello bird
    val words = lines.flatMap(line => {
      line.split(" ")
    })
    //hello
    //boy
    //hello
    //girl
    //hello
    //spark
    //##batch-interval##
    //hello
    //cat
    //hello
    //hello
    //bird
    val wordAndCount = words.map((_, 1)).reduceByKey((a, b) => {
      Thread.sleep(500)
      a + b
    })
    //(hello,3)
    //(boy,1)
    //(girl,1)
    //##batch-interval##
    //
    wordAndCount.print(10)
    ssc.start()
    ssc.awaitTermination()
  }

}
