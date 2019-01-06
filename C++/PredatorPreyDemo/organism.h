#ifndef ORGANISM_H
#define ORGANISM_H

#include "grid.h"

class organism {
protected:
    grid g;
public:
    organism(grid& g);
    virtual void move() { }
    virtual bool isDead() const;
    virtual char whichOne() const { return '\0'; }
    virtual void revive() { }
    virtual ~organism() { }//cout<<"parent class destructor called!!"<<endl; }
};

#endif // ORGANISM_H
