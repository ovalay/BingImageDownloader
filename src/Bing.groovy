import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created by Tim on 14/10/15.
 */

outputDir = "bing/"
params = [format:"xml",
          idx:0,
          n:1,
          mkt:"en-US"]   //format can be xml, js or rss, market can be us, uk, jp, etc..

paramString = params.collect {k,v ->
    "$k=$v"
}.join("&")

def url = "http://www.bing.com/HPImageArchive.aspx?"+paramString

def root = new XmlSlurper().parseText(url.toURL().text)

def imageURL = "http://www.bing.com${root.image.url.text()}"

imageURL = imageURL.replace("1366x768", "1920x1200")   //get the big version of the pic instead of the small

def name = (root.image.startdate.text() << ".jpg")    //problems on mac writing to a different dir, so just use same dir for now
BufferedImage image = ImageIO.read(imageURL.toURL())
File output = new File(name.toString())
ImageIO.write(image, "jpg", output)
