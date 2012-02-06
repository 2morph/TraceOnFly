@ECHO OFF

SET blenderPath="C:\Program Files (x86)\Blender Foundation\Blender"
SET projectPath="C:\Users\admin\Documents\uni\Bachelor\workspace\Blender"
SET defaultProject=test.blend
SET imageFile=output\test

SET script=testscript.py


ECHO Start Rendering file with Blender:
ECHO using script: %script%
ECHO write file : %imageFile%0001.png
ECHO ******************************************
cd %projectPath%
%blenderPath%\blender.exe --background %defaultProject% --render-output %imageFile% --python %script% --render-frame 1