import random
import argparse


def parse_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '-n', 
        '--number-of-particles',
        type=int,
        required=True
    )
    parser.add_argument(
        '-L',
        '--length', 
        type=int,
        required=True
    )
    parser.add_argument(
        '-s',
        '--static-output',
        required=False
    )
    parser.add_argument(
        '-d',
        '--dynamic-output',
        required=False
    )
    
    args = parser.parse_args()
    if args.number_of_particles <= 0:
        raise 'Invalid number of particles'
    if args.length is not None and args.length <= 0:
        raise 'Invalid length'
    return args


def get_static_filepath(filepath):
    if filepath is not None:
        return filepath
    return 'generated.static.txt'


def get_dynamic_filepath(filepath):
    if filepath is not None:
        return filepath
    return 'generated.dynamic.txt'


def generate_particles(number_of_particles, L):
    if L is None:
        L = random.randint(100, 1000)
    particles = []
    for i in range(number_of_particles):
        particles.append({
            'radius': random.random(),
            'property': random.random(),
            'position': {
                'x': random.random() * L,
                'y': random.random() * L
            }
        })
    return particles, L


def dump_static_file(particles, L, filepath):
    with open(filepath, 'w') as f:
        f.write(str(len(particles)))
        f.write('\n')
        f.write(str(L))
        f.write('\n')
        for p in particles:
            f.write(str(p['radius']))
            f.write('\t')
            f.write(str(p['property']))
            f.write('\n')


def dump_dynamic_file(particles, filepath):
    with open(filepath, 'w') as f:
        f.write('0\n')
        for p in particles:
            f.write(str(p['position']['x']))
            f.write('\t')
            f.write(str(p['position']['y']))
            f.write('\n')


def main():
    random.seed()

    args = parse_arguments()
    particles, L = generate_particles(
        args.number_of_particles,
        args.length
    )
    dump_static_file(particles, L, get_static_filepath(args.static_output))
    dump_dynamic_file(particles, get_dynamic_filepath(args.dynamic_output))


if __name__ == '__main__':
    main()
