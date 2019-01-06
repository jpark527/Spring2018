#include "doodlebug.h"

// Private
void doodleBug::starve() {
    if(count>2) {
        count=-1;
        g.remove(row,col);
        starved = true;
    }
}

// Public
doodleBug::doodleBug(grid &g) : organism(g) {
    starved = false;
    count=0;
    while(g.isOccupied(row=rand()%20, col=rand()%20));
    g.add(0,row,col);
}

doodleBug::~doodleBug() {
    starved=false;
    row=col=count=0;
}

bool doodleBug::isDead() const {
    return starved;
}

void doodleBug::move() {
    if(starved)
        return;
    ++count;
    int prevRow = row, prevCol = col;
    switch (rand()%4) {
    case 0:
        row+=1;
        break;
    case 1:
        row-=1;
        break;
    case 2:
        col+=1;
        break;
    case 3:
        col-=1;
    }
    if(row<0 || col<0 || row>ROW-1 || col>COL-1 || g.isOccupied(row, col)) {
        if(row>=0 && col>=0 && row<ROW && col<COL && g.get(row,col)=='o')
            count = 0;
        else {
            row = prevRow;
            col = prevCol;
        }
    }
    g.remove(prevRow, prevCol);
    g.add(0,row,col);
    starve();
}

char doodleBug::whichOne() const {
    return 'd';
}

void doodleBug::revive() {
    starved = false;
    count=0;
    while(g.isOccupied(row=rand()%20, col=rand()%20));
    g.add(0,row,col);
}
