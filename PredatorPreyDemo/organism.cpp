#include "organism.h"

organism::organism(grid &g) {
    this->g = g;
}

bool organism::isDead() const {
    return false;
}
