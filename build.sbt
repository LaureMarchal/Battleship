ThisBuild / scalaVersion := "2.12.6"
ThisBuild / organization := "com.example"

lazy val battleship = (project in file("."))
  .settings(
    name := "Battleship",
    libraryDependencies ++= Seq("au.com.bytecode" % "opencsv" % "2.4", "org.scalatest" %% "scalatest" % "3.0.5" % Test),
  )


