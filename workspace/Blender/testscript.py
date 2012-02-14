import bpy
import mathutils


# Cube = bpy.data.objects["Cube"]
# Cube.delta_location = mathutils.Vector((-3, -3, -3))
#list(bpy.data.worlds)
world = bpy.data.worlds["World"]
world.use_sky_paper = True
img = bpy.data.images.load("c:\\test\\cam.jpg")
tex = bpy.data.textures.new("myTex", type="IMAGE")
tex.image=img
slot = world.texture_slots.add()
slot.texture=tex
slot.use_map_horizon=True