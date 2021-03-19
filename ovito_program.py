import os
import sys


# Recibe la linea donde se encuentra el valor del radio junto con la propiedad y devuelve el radio
def getRadius(particle_radius):
    j = 0
    while particle_radius[j].isdigit() == False:
        j += 1
    particle_radius = particle_radius.split('\t')
    return particle_radius[j]

# recibe el id de la particula de la cual queremos calcular los vecinos y el output file (file de los vecinos)
# retorna un array de las particulas que son vecinas
def getVecinos(id_particle, file):
    vecinos = open(file, "r")
    line = vecinos.readline()
    out = True
    while out:
        line = vecinos.readline()
        line = line.split('\t')
        line[0] = line[0].replace('\n', '')
        if line[0] == str(id_particle):
            out = False
    ret = [id_particle]
    for segment in line:
        segment = segment.replace('\n','')
        string =  str(id_particle)
        if segment.isdigit() and segment != string:
            ret.append(int(segment))
    return ret
    






# Get the total number of args passed
total = len(sys.argv)
if total != 5:
    print("four arguments needed, 1.static file, 2.dynamic file 3.vecinos file 4.id of particle")
    quit()


# Ovito file will later be uploaded to ovito program
index = 1
ovito_file = "ovito." + str(index) + ".txt"
while os.path.exists(ovito_file):
  os.remove(ovito_file)
  index += 1
 

static_file = str(sys.argv[1])
dynamic_file = str(sys.argv[2])
vecinos_file = str(sys.argv[3])
id_particle = int(sys.argv[4])


#open the files
static = open(static_file, "r")
dynamic = open(dynamic_file, "r")



index = 1
ovito_file = "ovito." + str(index) + ".txt"
ovito = open(ovito_file, "a")

# first line tells us N (the number of particles)
N = static.readline()

# Delete spaces if there are any
N = N.replace(' ', '')
ovito.write(N)
ovito.write('\n')

#we dont need L
static.readline()
dynamic.readline()


vecinos = getVecinos(id_particle, vecinos_file)
print("vecinos")
print(vecinos)

particle_id=0
for line in static:
    ovito.write(str(particle_id))
    ovito.write('\t')
    particle_radius = line.replace('\n', '')
    particle_radius = getRadius(particle_radius)
    ovito.write(particle_radius)
    ovito.write('\t')
    ovito.write(dynamic.readline().replace('\n',''))
    if particle_id in vecinos:
        ovito.write('\t' + "0" + '\t' +  "250" + '\n')
    else:
        ovito.write('\t' + "250" + '\t' +  "0" + '\n')
    particle_id += 1
  
ovito.close()
static.close()


