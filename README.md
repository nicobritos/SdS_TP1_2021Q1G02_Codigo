# SS-2021Q1G02-TP1

## Java simulation execution

In order to execute the simulation step inside the "java" folder and run

```
mvn package
```

### Program arguments
##### Required

* `-DstaticPath="./path/to/static/file"`: Specifies the path of the static file

* `-DdynamicPath="./path/to/dynamic/file"`: Specifies the path of the dynamic file

* `-Dr=10.0`: Specifies the interaction's radius. It must be a
  positive number (integer or float)

##### Optional

* `-DparseVelocity`: If present the program will try to parse the 
  velocity fieds in the dynamic file

* `-Dperiodic`: If present the neighbors and distance will be calculated 
  using a periodic function. Otherwise, they
  will be calculated using the Pythagorean formula (direct distance)

* `-Dbruteforce`: If present the program will disregard the specified matrix size
  (if any) and the particles' neighbors will be
  calculated comparing every particle against each other. 
  If not, the program will use the Cell Index Method to do so.

* `-DM=10`: Specifies the matrix size when using the Cell Index Method. 
  If the bruteforce method is chosen then this argument will be disregarded.
  The value must be a positive integer. Please note that if this argument 
  doesn't verify that `L/M > rc + max(r)` (where `L` is the length of both axis 
  of the area of simulation specified in the static file, `rc` is the interaction
  radius and `max(r)` is the maximum radius of all the particles) then an error
  will the thrown.
