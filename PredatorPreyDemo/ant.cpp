#include "ant.h"

ant::ant(grid &g) : organism(g){
    eaten = false;
    while(g.isOccupied(row=rand()%20, col=rand()%20));
    g.add(1,row,col);
}

ant::~ant() {
    eaten=false;
    row=col=0;
}

bool ant::isDead() const {
    return eaten;
}

void ant::move() {
    if(g.get(row,col)=='x' || eaten) {
        eaten = true;
        return;
    }
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
        row = prevRow;
        col = prevCol;
    }
    g.remove(prevRow, prevCol);
    g.add(1,row,col);
}

char ant::whichOne() const {
    return 'a';
}

void ant::revive() {
    eaten = false;
    while(g.isOccupied(row=rand()%20, col=rand()%20));
    g.add(1,row,col);
}
