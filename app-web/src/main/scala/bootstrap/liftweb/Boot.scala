package bootstrap.liftweb

import java.io.{File, FileNotFoundException}
import com.andrie.sample.App
import net.liftweb.http._
import net.liftweb.sitemap.Loc.LocGroup
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.sitemap.Menu.Menuable
import net.liftweb.util.NamedPF
import org.streum.configrity.Configuration
import scala.xml.Text


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */

class Boot {
    import bootstrap.liftweb.Boot._


    def boot():Unit = {
        LiftRules.supplementalHeaders.default.set(Nil)

        LiftRules.addToPackages("com.andrie.sample")

        val entries:List[Menuable] = List(
            Menu("Home", S.loc("Home", Text("Home"))) / "index" >> LocGroup("main-menu"),
            Menu("Update Data", S.loc("Update", Text("Update"))) / "update" >> LocGroup("main-menu"),
            Menu("View Data", S.loc("View", Text("View"))) / "view" >> LocGroup("main-menu")
        )

        // Setup Sitemap
        LiftRules.setSiteMap(SiteMap(entries:_*))

        LiftRules.statelessRewrite.append(NamedPF("AppRewrite") {
            case RewriteRequest(ParsePath("add" :: Nil, _, _, _), _, _) =>
                RewriteResponse("update" :: Nil)
            case RewriteRequest(ParsePath("view" :: id :: "edit" :: Nil, _, _, _), _, _) =>
                RewriteResponse("update" :: Nil, Map("id" -> id))
            case RewriteRequest(ParsePath("view" :: id :: Nil, _, _, _), _, _) =>
                RewriteResponse("view" :: Nil, Map("id" -> id))
        })

        // Penanganan untuk halaman 404
        LiftRules.uriNotFound.prepend(NamedPF("404handler") {
            case (req, failure) =>
                NotFoundAsTemplate(ParsePath(List("index"), "html", absolute = false, endSlash = false))
        })

        val conf = parseConfig()
        setupEngine(conf)
    }

    private def setupEngine(conf:Configuration):Unit = {
        App.setupBasicEngine(conf)
    }
}


object Boot {
    lazy val currentDir:File = {
        new File(new File(new File(new File(getClass.getProtectionDomain.getCodeSource.getLocation.getPath)
            .getParent).getParent).getParent)
    }

    lazy val appConfFile:String = {
        System.getProperty("app.configFile", {
            val conf = currentDir + "/app.conf"
            if (new File(conf).exists()) {
                conf
            } else {
                currentDir.getParent + "app.conf"
            }
        })
    }

    def parseConfig():Configuration = {
        try {
            val conf = Configuration.load(appConfFile)
            conf
        } catch {
            case e:FileNotFoundException =>
                e.printStackTrace()
                System.exit(4)
                throw e
        }
    }
}

