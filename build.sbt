ThisBuild / scalaVersion := "2.12.6"
ThisBuild / organization := "com.example"

lazy val battleship = (project in file("."))
  .settings(
    name := "Battleship",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  )


