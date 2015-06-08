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
			val input = sc.textFile(warAndPeace)

			val wc = input
						.map(_.toLowerCase)
						.flatMap(_.split("""\W+"""))
						.countByValue()


			val outpath = "word-count-out"
			println("Writing ${wc.size} records to output")

			val out = new java.io.PrintWriter(outpath)
			wc foreach {
				case (word,count) =>
					out.println("%20s\t%d".format(word,count))
			}
			out.close()
			
		} finally {
			sc.stop()
		}
		
	}
}
