object Tilengine {
  // CreateWindow flags
  val CWF_FULLSCREEN = (1 << 0)
  val CWF_VSYNC = (1 << 1)
  val CWF_S1 = (1 << 2)
  val CWF_S2 = (2 << 2)
  val CWF_S3 = (3 << 2)
  val CWF_S4 = (4 << 2)
  val CWF_S5 = (5 << 2)
  val CWF_NEAREST = (6 << 2)

  // error codes
  val TLN_ERR_OK = 0 // No error
  val TLN_ERR_OUT_OF_MEMORY = 1 // Not enough memory
  val TLN_ERR_IDX_LAYER = 2 // Layer index out of range
  val TLN_ERR_IDX_SPRITE = 3 // Sprite index out of range
  val TLN_ERR_IDX_ANIMATION = 4 // Animation index out of range
  val TLN_ERR_IDX_PICTURE = 5 // Picture or tile index out of range
  val TLN_ERR_REF_TILESET = 6 // Invalid TLN_Tileset reference
  val TLN_ERR_REF_TILEMAP = 7 // Invalid TLN_Tilemap reference
  val TLN_ERR_REF_SPRITESET = 8 // Invalid TLN_Spriteset reference
  val TLN_ERR_REF_PALETTE = 9 // Invalid TLN_Palette reference
  val TLN_ERR_REF_SEQUENCE = 10 // Invalid TLN_SequencePack reference
  val TLN_ERR_REF_SEQPACK = 11 // Invalid TLN_Sequence reference
  val TLN_ERR_REF_BITMAP = 12 // Invalid TLN_Bitmap reference
  val TLN_ERR_NULL_POINTER = 13 // Null pointer as argument
  val TLN_ERR_FILE_NOT_FOUND = 14 // Resource file not found
  val TLN_ERR_WRONG_FORMAT = 15 // Resource file has invalid format
  val TLN_ERR_WRONG_SIZE = 16 // A width or height parameter is invalid
  val TLN_ERR_UNSUPPORTED = 17 // Unsupported function
  val TLN_ERR_REF_LIST = 18 // Invalid TLN_ObjectList reference

  // SetLayerBlendMode & SetSpriteBlendMode modes
  val BLEND_NONE = 0
  val BLEND_MIX = 1
  val BLEND_ADD = 2
  val BLEND_SUB = 3
  val BLEND_MOD = 4

  // Player
  val PLAYER1 = 0
  val PLAYER2 = 1
  val PLAYER3 = 2
  val PLAYER4 = 3

  // GetInput
  val INPUT_NONE = 0
  val INPUT_UP = 1
  val INPUT_DOWN = 2
  val INPUT_LEFT = 3
  val INPUT_RIGHT = 4
  val INPUT_BUTTON1 = 5
  val INPUT_BUTTON2 = 6
  val INPUT_BUTTON3 = 7
  val INPUT_BUTTON4 = 8
  val INPUT_BUTTON5 = 9
  val INPUT_BUTTON6 = 10
  val INPUT_START = 11
  val INPUT_QUIT = 12
  val INPUT_CRT = 13

  // player request modifier flag
  val INPUT_P1 = (PLAYER1 << 5)
  val INPUT_P2 = (PLAYER2 << 5)
  val INPUT_P3 = (PLAYER3 << 5)
  val INPUT_P4 = (PLAYER4 << 5)

  // compatibility symbols for pre-1.18 input model
  val INPUT_A = INPUT_BUTTON1
  val INPUT_B = INPUT_BUTTON2
  val INPUT_C = INPUT_BUTTON3
  val INPUT_D = INPUT_BUTTON4
  val INPUT_E = INPUT_BUTTON5
  val INPUT_F = INPUT_BUTTON6

  // overlays for CRT effect

  val TLN_OVERLAY_NONE = 0 /*!< no overlay */
  val TLN_OVERLAY_SHADOWMASK = 1 /*!< Shadow mask pattern */
  val TLN_OVERLAY_APERTURE = 2 /*!< Aperture grille pattern */
  val TLN_OVERLAY_SCANLINES = 3 /*!< Scanlines pattern */
  val TLN_OVERLAY_CUSTOM =
    4 /*!< User-provided when calling TLN_CreateWindow */
  val TLN_MAX_OVERLAY = 5
}

class Tilengine {
  System.loadLibrary("TilengineJNI")

  // affine transform
  final class Affine(
      angle: Float, // rotation
      dx: Float, // translation
      dy: Float, // translation
      sx: Float, // scale
      sy: Float // scale
  )

  // tile
  final class Tile(
      index: Short, // tile index
      flags: Short // attributes
  )

  // color strip
  final class ColorStrip(
      delay: Int, // time delay between frames
      first: Short, // index of first color to cycle
      count: Short, // number of colors in the cycle
      dir: Byte // direction: 0=descending, 1=ascending
  )

  // sequence info returned by GetSequenceInfo
  final class SequenceInfo(
      name: String, // sequence name
      num_frames: Int // number of frames
  )

  // Sprite creation info for CreateSpriteset
  final class SpriteData(
      name: String, // entry name
      x: Int, // horizontal position
      y: Int, // vertical position
      w: Int, // width
      h: Int // height
  )

  final class SpriteInfo(
      offset: Int,
      w: Int,
      h: Int
  )

  // Tile information returned by GetLayerTile
  final class TileInfo(
      index: Short,
      flags: Short,
      row: Int,
      col: Int,
      xoffset: Int,
      yoffset: Int,
      color: Byte,
      _type: Byte,
      empty: Boolean
  )

  // Object item info returned by GetObjectInfo
  final class ObjectInfo(
      id: Short, // unique ID
      gid: Short, // graphic ID (tile index)
      flags: Short, // attributes (FLAG_FLIPX, FLAG_FLIPY, FLAG_PRIORITY)
      x: Int, // horizontal position
      y: Int, // vertical position
      width: Int, // horizontal size
      height: Int, // vertical size
      _type: Byte, // type property
      visible: Boolean, // visible property
      name: String // name property
  )

// basic management
  @native def Init(
      hres: Int,
      vres: Int,
      numlayers: Int,
      numsprites: Int,
      numanimations: Int
  ): Int
  @native def Deinit: Unit
  @native def GetNumObjects: Int
  @native def GetUsedMemory: Int
  @native def GetVersion: Int
  @native def GetNumLayers: Int
  @native def GetNumSprites: Int
  @native def SetBGColor(r: Int, g: Int, b: Int): Unit
  @native def SetBGBitmap(bitmap: Int): Boolean
  @native def SetBGPalette(palette: Int): Boolean
  @native def SetRasterCallback(obj: Object, methodname: String): Unit
  @native def SetRenderTarget(data: Array[Int], pitch: Int): Unit
  @native def UpdateFrame(frame: Int): Unit
  @native def SetLoadPath(path: String): Unit
  @native def SetWindowTitle(title: String): Unit

  // error handling
  @native def SetLastError(error: Int): Unit
  @native def GetLastError: Int
  @native def GetErrorString(error: Int): String

  // window management
  @native def CreateWindow(overlay: String, flags: Int): Boolean
  @native def CreateWindowThread(overlay: String, flags: Int): Boolean
  @native def ProcessWindow: Boolean
  @native def IsWindowActive: Boolean
  @native def GetInput(id: Int): Boolean
  @native def DrawFrame(frame: Int): Unit
  @native def WaitRedraw: Unit
  @native def DeleteWindow: Unit
  @native def EnableBlur(mode: Boolean): Unit
  @native def GetTicks: Int
  @native def Delay(time: Int): Unit
  @native def BeginWindowFrame: Unit
  @native def EndWindowFrame: Unit
  @native def GetWindowHeight: Int
  @native def GetWindowWidth: Int

  // spritesets management
  @native def LoadSpriteset(name: String): Int
  @native def CloneSpriteset(src: Int): Int
  @native def GetSpriteInfo(spriteset: Int, entry: Int, info: SpriteInfo): Boolean
  @native def GetSpritesetPalette(spriteset: Int): Int
  @native def DeleteSpriteset(spriteset: Int): Boolean

  // tilesets management
  @native def LoadTileset(filename: String): Int
  @native def CloneTileset(src: Int): Int
  @native def SetTilesetPixels(
      tileset: Int,
      entry: Int,
      srcdata: Array[Byte],
      srcpitch: Int
  ): Boolean
  @native def CopyTile(tileset: Int, src: Int, dst: Int): Boolean
  @native def GetTileWidth(tileset: Int): Int
  @native def GetTileHeight(tileset: Int): Int
  @native def GetTilesetPalette(tileset: Int): Int
  @native def DeleteTileset(tileset: Int): Boolean
  @native def GetTilesetNumTiles(tileset: Int): Int

  // tilemaps management
  @native def LoadTilemap(filename: String, layername: String): Int
  @native def CloneTilemap(src: Int): Int
  @native def GetTilemapRows(tilemap: Int): Int
  @native def GetTilemapCols(tilemap: Int): Int
  @native def GetTilemapTile(tilemap: Int, row: Int, col: Int, tile: Tile): Boolean
  @native def SetTilemapTile(tilemap: Int, row: Int, col: Int, tile: Tile): Boolean
  @native def CopyTiles(
      src: Int,
      srcrow: Int,
      srccol: Int,
      rows: Int,
      cols: Int,
      dst: Int,
      dstrow: Int,
      dstcol: Int
  ): Unit
  @native def DeleteTilemap(tilemap: Int): Boolean

  // color tables management
  @native def CreatePalette(entries: Int): Int
  @native def LoadPalette(filename: String): Int
  @native def ClonePalette(src: Int): Int
  @native def DeletePalette(palette: Int): Unit
  @native def SetPaletteColor(palette: Int, color: Int, r: Byte, g: Byte, b: Byte): Unit
  @native def MixPalettes(src1: Int, src2: Int, dst: Int, f: Byte): Unit

  // bitmaps
  @native def CreateBitmap(width: Int, height: Int, bpp: Int): Int
  @native def LoadBitmap(filename: String): Int
  @native def CloneBitmap(src: Int): Int
  @native def GetBitmapWidth(bitmap: Int): Int
  @native def GetBitmapHeight(bitmap: Int): Int
  @native def GetBitmapDepth(bitmap: Int): Int
  @native def GetBitmapPitch(bitmap: Int): Int
  @native def GetBitmapPalette(bitmap: Int): Int
  @native def SetBitmapPalette(bitmap: Int, palette: Int): Boolean
  @native def DeleteBitmap(bitmap: Int): Boolean

  // layer management
  @native def SetLayer(nlayer: Int, tileset: Int, tilemap: Int): Boolean
  @native def SetLayerPalette(nlayer: Int, palette: Int): Boolean
  @native def SetLayerPosition(nlayer: Int, hstart: Int, vstart: Int): Boolean
  @native def SetLayerScaling(nlayer: Int, xfactor: Float, yfactor: Float): Boolean
  @native def SetLayerAffineTransform(nlayer: Int, affine: Affine): Boolean
  @native def SetLayerTransform(
      layer: Int,
      angle: Float,
      dx: Float,
      dy: Float,
      sx: Float,
      sy: Float
  ): Boolean
  @native def SetLayerBlendMode(nlayer: Int, mode: Int, factor: Byte): Boolean
  @native def SetLayerColumnOffset(nlayer: Int, offset: Array[Int]): Boolean
  @native def SetLayerClip(nlayer: Int, x1: Int, y1: Int, x2: Int, y2: Int): Boolean
  @native def DisableLayerClip(nlayer: Int): Boolean
  @native def ResetLayerMode(nlayer: Int): Boolean
  @native def DisableLayer(nlayer: Int): Boolean
  @native def GetLayerPalette(nlayer: Int): Int
  @native def GetLayerTile(nlayer: Int, x: Int, y: Int, info: TileInfo): Boolean
  @native def SetLayerPriority(nlayer: Int, enable: Boolean): Boolean
  @native def SetLayerParent(nlayer: Int, parent: Int): Boolean
  @native def DisableLayerParent(nlayer: Int): Boolean
  @native def SetLayerTilemap(nlayer: Int, tilemap: Int): Boolean
  @native def SetLayerMosaic(nlayer: Int, width: Int, height: Int): Boolean
  @native def DisableLayerMosaic(nlayer: Int): Boolean
  @native def GetLayerWidth(nlayer: Int): Int
  @native def GetLayerHeight(nlayer: Int): Int

  // sprites management
  @native def ConfigSprite(nsprite: Int, spriteset: Int, flags: Short): Boolean
  @native def SetSpriteSet(nsprite: Int, spriteset: Int): Boolean
  @native def SetSpriteFlags(nsprite: Int, flags: Short): Boolean
  @native def SetSpritePosition(nsprite: Int, x: Int, y: Int): Boolean
  @native def SetSpritePicture(nsprite: Int, entry: Int): Boolean
  @native def SetSpritePalette(nsprite: Int, palette: Int): Boolean
  @native def SetSpriteBlendMode(nsprite: Int, mode: Int, factor: Byte): Boolean
  @native def SetSpriteScaling(nsprite: Int, sx: Float, sy: Float): Boolean
  @native def ResetSpriteScaling(nsprite: Int): Boolean
  @native def GetSpritePicture(nsprite: Int): Int
  @native def GetAvailableSprite: Int
  @native def EnableSpriteCollision(nsprite: Int, enable: Boolean): Boolean
  @native def GetSpriteCollision(nsprite: Int): Boolean
  @native def DisableSprite(nsprite: Int): Boolean
  @native def GetSpritePalette(nsprite: Int): Int
  @native def EnableSpriteFlag(nsprite: Int, flag: Short, enable: Boolean): Boolean
  @native def SetFirstSprite(nsprite: Int): Boolean
  @native def SetNextSprite(nsprite: Int, next: Int): Boolean
  @native def EnableSpriteMasking(nsprite: Int, enable: Boolean): Boolean
  @native def SetSpritesMaskingRegion(top_line: Int, bottom_line: Int): Unit
  @native def SetSpriteAnimation(nsprite: Int, sequence: Int, loop: Int): Boolean
  @native def DisableSpriteAnimation(nsprite: Int): Boolean

  // sequences management
  @native def CloneSequence(src: Int): Int
  @native def DeleteSequence(sequence: Int): Boolean

  // sequence pack management
  @native def CreateSequencePack: Int
  @native def LoadSequencePack(filename: String): Int
  @native def FindSequence(sp: Int, name: String): Int
  @native def AddSequenceToPack(sp: Int, sequence: Int): Boolean
  @native def DeleteSequencePack(sp: Int): Boolean

  // animation engine
  @native def SetPaletteAnimation(
      index: Int,
      palette: Int,
      sequence: Int,
      blend: Boolean
  ): Boolean
  @native def SetPaletteAnimationSource(index: Int, palette: Int): Boolean
  @native def GetAnimationState(index: Int): Boolean
  @native def SetAnimationDelay(index: Int, frame: Int, delay: Int): Boolean
  @native def GetAvailableAnimation: Int
  @native def DisablePaletteAnimation(index: Int): Boolean
}