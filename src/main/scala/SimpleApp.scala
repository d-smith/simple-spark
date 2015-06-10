import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
	def main(args: Array[String]) {
		val warAndPeace = "war_and_peace.txt" // Should be some file on your system


		//Spark setup
		val conf = new SparkConf().setAppName("Simple Application").setMaster("local[2]")
		val sc = new SparkContext(conf)


		try {
			//Read war and peace
			val input = sc.textFile(warAndPeace).map(_.toLowerCase)

			val wc = input
						.flatMap(_.split("""\W+"""))
						.map(word => (word,1))
						.reduceByKey((_+_))
						.keyBy(t => t._2)
						.sortByKey(false)
						.map(_._2)

			val thing = wc.collect().take(20) foreach println


			val outpath = "word-count-out"
			println("Writing ${wc.size} records to output")

			wc.saveAsTextFile(outpath)
			
		} finally {
			sc.stop()
		}
		
	}
}
