import sbt._
import Keys._
import xerial.sbt.Pack._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
object Build extends Build {
    import BuildSettings._
    import Dependencies._


    // root
    lazy val root = Project("sample-app", file("."))
        .aggregate(appCore, appWeb)
        .settings(basicSettings: _*)
        .settings(noPublishing: _*)

    // modules
    lazy val appCore = Project("app-core", file("app-core"))
        .settings(moduleSettings: _*)
        .settings(packAutoSettings: _*)
        .settings(libraryDependencies ++=
            compile(apacheCommons, slick, configrity) ++
            test(specs2) ++
            runtime(logbackClassic, postgres)
        )

    lazy val appWeb = Project("app-web", file("app-web"))
        .settings(moduleSettings: _*)
        .settings(packSettings: _*)
        .settings(packResourceDir ++= Map(
            (baseDirectory.value / "src/main/webapp") -> "webapp",
            new File("etc/jetty_instance") -> "jetty_instance",
            new File("etc/example.conf") -> "example.conf"
        ))
        .settings(com.earldouglas.xsbtwebplugin.WebPlugin.webSettings: _*)
        .settings(com.earldouglas.xsbtwebplugin.PluginKeys.port in com.earldouglas.xsbtwebplugin.WebPlugin.container.Configuration := 8080)
        .settings(libraryDependencies ++=
            liftDeps ++
                test(specs2) ++
                runtime(logbackClassic, postgres)
        ).dependsOn(appCore)
}
