import sbt._
import Keys._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
object BuildSettings {

    lazy val basicSettings = seq(
        version                 := "0.0.1",
        homepage                := Some(new URL("http://localhost:8080")),
        organization            := "com.andrie.sample",
        organizationHomepage    := Some(new URL("http://localhost:8080")),
        description             := "Sample App",
        scalaVersion            := "2.11.2",
        resolvers               ++= Dependencies.resolutionRepos,
        scalacOptions           := Seq("-deprecation", "-encoding", "utf8")
    )

    lazy val moduleSettings = basicSettings ++ seq(
        // scaladoc settings
        (scalacOptions in doc) <++= (name, version).map((n, v) => Seq("-doc-title", n, "-doc-version", v)),

        // publishing
        credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
        crossPaths := false,
        publishMavenStyle := true
    )

    lazy val noPublishing = seq(
        publish := (),
        publishLocal := ()
    )

}
