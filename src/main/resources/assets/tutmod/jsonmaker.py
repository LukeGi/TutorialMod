import sys
import os
import re

modid = 'tutmod'
block_file = 'blocks.txt'
item_file = 'items.txt'

default_blockstate = '''{
  "forge_marker":1,
  "defaults":{
    "transform":"forge:default-block",
    "model":"cube_all",
    "textures":{
      "all": "{0}:blocks/{1}"
    }
  },
  "variants":{
    "inventory":[{}],
    "normal":[{}]
  }
}'''.split('\n')

default_itemmodel = '''{
    "parent": "item/generated",
    "textures": {
        "layer0": "{0}:items/{1}"
    }
}'''.split('\n')
def makedirscheck(name):
   if not os.path.exists(name):
      os.makedirs(name)

def checkAllDirs():
   makedirscheck('blockstates')
   makedirscheck('models/item')

def initFile(name):
   with open(name, 'w') as f:
      f.close()

def initFiles():
   initFile(block_file)
   initFile(item_file)
	
def populate(fil3):
   todolist = []
   with open(fil3, 'r') as f:
      for line in f:
         todolist.append(line.strip('\n'))
   f.close
   return todolist

def writeBlockJSONS(todolist):
   for block in todolist:
      with open('blockstates/{0}.json'.format(block), 'w') as f:
         for line in default_blockstate:
            if not line == default_blockstate[6]:
               f.write(line + '\n')
            else:
               f.write(line.format(modid, block) + '\n')
      f.close()
      print 'Written {0}.json'.format(block)
      
def writeItemJSONS(todolist):
   for item in todolist:
      with open('models/item/{0}.json'.format(item), 'w') as f:
         for line in default_itemmodel:
            if not line == default_itemmodel[3]:
               f.write(line + '\n')
            else:
               f.write(line.format(modid, item) + '\n')
      f.close()
      print 'Written {0}.json'.format(item)

def writeLang(blocks, items):   
   with open('lang/en_US.lang', 'w') as f:
      f.write('#-------- BLOCKS --------\n')
      for block in blocks:
         f.write('tile.{0}:{1}.name={2}'.format(modid, block, (block.replace('_', ' ').title())) + '\n')
         print 'Written {0}\'s lang'.format(block)
      f.write('#-------- Items --------\n')
      for item in items:
         f.write('item.{0}:{1}.name={2}'.format(modid, item, (item.replace('_', ' ').title())) + '\n')
         print 'Written {0}\'s lang'.format(item)
   f.close()

def main():
   checkAllDirs()
   if not (os.path.isfile(block_file)):
      initFile(block_file)
   if not (os.path.isfile(item_file)):
      initFile(item_file)
   writeBlockJSONS(populate(block_file))
   writeItemJSONS(populate(item_file))
   writeLang(populate(block_file), populate(item_file))
   print 'COMPLETED ALL TASKS'

main()
