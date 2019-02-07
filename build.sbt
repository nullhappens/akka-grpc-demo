val compilerFlags = Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:postfixOps", // allow postfix operators
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint",               // enable handy linter warnings
//  "-Xfatal-warnings",     // turn compiler warnings into errors
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

// Common settings
ThisBuild / organization := "com.nullhappens.grpc"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / autoCompilerPlugins := true
ThisBuild / scalacOptions ++= compilerFlags
ThisBuild / scalafmtOnCompile := true

lazy val root = (project in file(".")).aggregate(`grpc-common`,`grpc-demo-server`, `grpc-demo-client`)

lazy val `grpc-common` = (project in file("./grpc-common"))
  .enablePlugins(AkkaGrpcPlugin)

lazy val `grpc-demo-server` = (project in file("./grpc-server"))
  .settings(
    javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"
  )
  .enablePlugins(JavaAgent)
  .dependsOn(`grpc-common`)

lazy val `grpc-demo-client` = (project in file("./grpc-client"))
  .dependsOn(`grpc-common`)
