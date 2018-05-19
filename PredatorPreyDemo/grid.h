#ifndef GRID_H
#define GRID_H

#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>

using namespace std;

#define ROW 20
#define COL 20

enum ERROR { OUT_OF_BOUND };

class grid {
    char** g;
public:
    grid();
    grid& operator=(const grid& copy);
    bool isOccupied(const int& row, const int& col) const;
    void add(const bool& critter, const int& row, const int& col);
    void remove(const int& row, const int& col);
    char get(const int& row, const int& col) const;
    void clear(const int& row, const int& col) const;
    void printGrid();
    ~grid();

    static bool last;
};

#endif // GRID_H
