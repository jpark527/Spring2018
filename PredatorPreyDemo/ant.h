#ifndef ANT_H
#define ANT_H

#include "grid.h"
#include "organism.h"

class ant : public organism {
private:
    bool eaten;
    int row, col;
public:
    ant(grid& g);
    virtual ~ant();
    virtual bool isDead() const;
    virtual void move();
    virtual char whichOne() const;
    virtual void revive();
};

#endif // ANT_H
