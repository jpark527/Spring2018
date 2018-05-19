#ifndef DOODLEBUG_H
#define DOODLEBUG_H

#include "grid.h"
#include "organism.h"

class doodleBug : public organism {
private:
    int row, col, count;
    bool starved;
    void starve();
public:
    doodleBug(grid& g);
    virtual ~doodleBug();
    virtual bool isDead() const;
    virtual void move();
    virtual char whichOne() const;
    virtual void revive();
};

#endif // DOODLEBUG_H
