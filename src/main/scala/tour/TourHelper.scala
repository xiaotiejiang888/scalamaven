/*
 * Copyright 2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tour

import org.apache.spark.{SparkConf, SparkContext}

import com.mongodb.spark.MongoConnector
import com.mongodb.spark.config.WriteConfig

/**
 * A helper for the tour
 */
private[tour] trait TourHelper {

  def getSparkContext(args: Array[String]): SparkContext = {
    val uri: String = args.headOption.getOrElse("mongodb://mpush:talkingdata@172.23.6.82/mpush")
    val conf = new SparkConf()
      .setMaster("spark://172.23.5.113:7077")
      .setAppName("MongoSparkConnectorTour")
      .set("spark.app.id", "MongoSparkConnectorTour")
      .set("spark.mongodb.input.uri", uri)
      .set("spark.mongodb.output.uri", uri)

    val sc = new SparkContext(conf)
    MongoConnector(sc).withDatabaseDo(WriteConfig(sc), {db => db.drop()})
    sc
  }

}
