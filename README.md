# scalatilengine

[![](https://img.shields.io/github/v/tag/thechampagne/scalatilengine?label=version)](https://github.com/thechampagne/scalatilengine/releases/latest) [![](https://img.shields.io/github/license/thechampagne/scalatilengine)](https://github.com/thechampagne/scalatilengine/blob/main/LICENSE)

Scala binding for **Tilengine** a 2D graphics engine with raster effects for retro/classic style game development.

### Example
```scala
object Main {
  def main(args: Array[String]): Unit = {
      val tile = new Tilengine()
      tile.Init(400, 240, 1, 0, 20)
      tile.SetLoadPath("assets")
  
      val foreground = tile.LoadTilemap("sonic_md_fg1.tmx", "Layer 1")
      tile.SetLayer(0, 0, foreground)
  
      var frame = 0
      tile.CreateWindow(null, 0)
      while (tile.ProcessWindow) {
      	    tile.DrawFrame(frame)
	    frame += 1
      }
      tile.Deinit
  }
}
```

### References
 - [Tilengine](https://github.com/megamarc/Tilengine)
 - [JTilengine](https://github.com/megamarc/JTilengine)

### License

This repo is released under the [MPL-2.0](https://github.com/thechampagne/scalatilengine/blob/main/LICENSE).
