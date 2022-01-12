import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.util.Random

object GeneratePeopleInfoHDFS {
  def getRandomGender():String={
    val rand = new Random()
    val randNum = rand.nextInt(2)+1
    if (randNum % 2 == 0) {"M"} else {"F"}
  }
  def main(args: Array[String]) {
    val outputFile =  "hdfs://localhost:9000/user/hadoop/peopleinfo.txt"
    //val outputFile1 = "file:///usr/local/spark/mycode/exercise/peopleage/peopleage1.txt"
    val conf = new SparkConf().setAppName("GeneratePeopleAgeHDFS").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rand = new Random()
    val array = new Array[String](1000)

    for (i<-1 to 1000){
      var height = rand.nextInt(230)
      if (height<50) {height = height + 50}
      var gender = getRandomGender()
      if (height <100 && gender == "M") {height = height+100}
      if (height <100 && gender == "F") {height = height+50}
      array(i-1)=i+" "+gender+" "+height
    }
    val rdd = sc.parallelize(array)
    rdd.foreach(println)
    rdd.saveAsTextFile(outputFile)
  }
}
