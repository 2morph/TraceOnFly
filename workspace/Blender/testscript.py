import bpy
import mathutils


Cube = bpy.data.objects["Cube"]
Cube.delta_location = mathutils.Vector((-3, -3, -3))
