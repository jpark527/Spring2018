#include "grid.h"

bool grid::last = false;

grid::grid() {
    g = new char*[ROW];
    for (int row = 0; row < ROW; ++row) {
        g[row] = new char[COL];
        for (int col = 0; col < COL; ++col)
            g[row][col]='\0';
    }
}

grid& grid::operator=(const grid& copy) {
    if(this!=&copy)
        this->g = copy.g;
    return *this;
}

bool grid::isOccupied(const int& row, const int& col) const {
    if(row<0 || col<0)
        throw OUT_OF_BOUND;
    return g[row%ROW][col%COL] != '\0';
}

void grid::add(const bool& critter, const int& row, const int& col) {
    if(critter)
        g[row][col] = 'o';
    else
        g[row][col] = 'x';
}

void grid::remove(const int& row, const int& col) {
    g[row][col] = '\0';
}

char grid::get(const int& row, const int& col) const {
    return g[row][col];
}

void grid::clear(const int& row, const int& col) const {
    g[row][col] = '\0';
}

void grid::printGrid() {
    cout << ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" << endl;
    for (int row = 0; row < ROW; ++row) {
        for (int col = 0; col < COL; ++col) {
            char mark = g[row][col]=='\0' ? ' ' : g[row][col];
            cout << "| " << mark << " ";
        }
        cout << "|" << endl;
    }
    cout << "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" << endl;
}

grid::~grid() {
    if(last) {
        for (int row = 0; row < ROW; ++row)
            for (int col = 0; col < COL; ++col)
                g[row][col]='\0';
        for (int row = 0; row < ROW; ++row)
            delete[] g[row];
        delete[] g;
    }
}
