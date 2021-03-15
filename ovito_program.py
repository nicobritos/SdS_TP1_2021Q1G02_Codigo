import os
import sys



def getRadius(particle_radius):
    j = 0
    while particle_radius[j].isdigit() == False:
        j += 1
    particle_radius = particle_radius.split(' ')
    return particle_radius[j]


# Get the total number of args passed
total = len(sys.argv)
if total != 4:
    print("three arguments needed, static file, dinamic file and id of particle")
    quit()


# Ovito file will later be uploaded to ovito program
index = 1
ovito_file = "ovito." + str(index) + ".txt"
while os.path.exists(ovito_file):
  os.remove(ovito_file)
  index += 1
 

static_file = str(sys.argv[1])
dynamic_file = str(sys.argv[2])
current_particle = str(sys.argv[3])

#open the files
static = open(static_file, "r")
dynamic = open(dynamic_file, "r")



index = 1
ovito_file = "ovito." + str(index) + ".txt"
ovito = open(ovito_file, "a")

# first line tells us N (the number of particles)
N = static.readline()

# Delete spaces if there are any
N.replace(' ', '')
ovito.write(N)
ovito.write('\n\n')

#we dont need L
static.readline()
dynamic.readline()

particle_id=1
for line in static:
    ovito.write(str(particle_id))
    ovito.write('\t')
    particle_radius = line.replace('\n', '')
    particle_radius = getRadius(particle_radius)
    ovito.write(particle_radius)
    ovito.write(dynamic.readline())
    
    particle_id += 1
  
ovito.close()
static.close()

