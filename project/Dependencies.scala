import sbt._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
object Dependencies {

    val resolutionRepos = Seq(
        "typesafe repo"     at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
        "Sonatype releases"      at "https://oss.sonatype.org/content/repositories/releases"
    )

    def compile   (deps:ModuleID*):Seq[ModuleID] = deps map (_ % "compile")
    def test      (deps:ModuleID*):Seq[ModuleID] = deps map (_ % "test")
    def runtime   (deps:ModuleID*):Seq[ModuleID] = deps map (_ % "runtime")

    val apacheCommons = "org.apache.commons" % "commons-lang3" % "3.4"
    val specs2 = "org.specs2" %%  "specs2-core" % "3.6.6"
    val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.0.9"

    val liftVersion = "2.6"
    lazy val liftDeps = {
        Seq(
            liftWebkit % "compile",
            "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,compile,test",
            "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile,test" artifacts Artifact("javax.servlet", "jar", "jar"),
            "ch.qos.logback"    % "logback-classic"     % "1.0.13"
        )
    }

    lazy val liftWebkit = "net.liftweb"  %% "lift-webkit" % liftVersion
    lazy val liftDepsNoContainer = liftDeps.filter(x => !(x.name.endsWith("webapp") || x.name.endsWith("servlet")))

    lazy val postgres = "org.postgresql" % "postgresql" % "9.4.1208.jre7"
    lazy val slick = "com.typesafe.slick" % "slick_2.11" % "3.1.1"

    lazy val configrity = "org.streum" %% "configrity-core" % "1.0.1"
}
