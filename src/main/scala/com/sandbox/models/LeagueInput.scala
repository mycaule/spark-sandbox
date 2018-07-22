package com.sandbox.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

case class LeagueInput(
    @JsonProperty("name") name: String,
    @JsonProperty("url") url: String
)

object LeagueInput {
  def getLeagues(path: String = "src/main/resources/leagues.yaml"): Seq[LeagueInput] = {
    val mapper = new ObjectMapper(new YAMLFactory())
    val inputStream = new java.io.FileInputStream(path)
    mapper.readValue(inputStream, classOf[Array[LeagueInput]]).toSeq
  }
}
