#include "grid.h"
#include "organism.h"
#include "ant.h"
#include "doodlebug.h"

#define MAX_CRITTERS 400

bool getInput(string& input);
void removeDeads(organism** crits);
void breed(const int& count, int& ants, int& doodleBugs, organism** crits, grid& g);
void numberOfCrits(const grid& g, int& ants, int& doodleBugs);
void initialize(organism** critters, grid& g);
void deleteAll(organism **critters);

int main() {
    srand(time(0));
    int count=0;
    grid g;
    string input;
    organism** critters = new organism*[MAX_CRITTERS];
    initialize(critters, g);
    while(getInput(input)) {
        int a=0, d=0;
        ++count;
        numberOfCrits(g,a,d);
        cout<<"\nant(s): "<<a<<endl<<"doodlebug(s): "<<d<<endl;
        g.printGrid();
        for(int i=0; i<MAX_CRITTERS; ++i)
            if(critters[i]!=nullptr)
                critters[i]->move();
        removeDeads(critters);
        a=d=0;
        numberOfCrits(g,a,d);
        breed(count, a, d, critters, g);
    }
    deleteAll(critters);
    return 0;
}

bool getInput(string& input) {
    cout<<"Press 'Enter' to continue, 'x' to exit.."<<endl;
    getline(cin,input);
    return input!="x" && input!="X";
}

void removeDeads(organism** crits) {
    for(int i=0; i<MAX_CRITTERS; ++i)
        if(crits[i]!=nullptr && crits[i]->isDead()) {
                delete crits[i];
                crits[i]=nullptr;
        }
}

void breed(const int& count, int& ants, int& doodleBugs, organism** crits, grid& g) {
    if(count>0 && !(count%3)) {
        for(int i=0; ants && i<MAX_CRITTERS; ++i)
            if(crits[i]==nullptr) {
                crits[i] = new ant(g);
                --ants;
            }
    } else if(count>0 && !(count%8)) {
        for(int i=0; doodleBugs && i<MAX_CRITTERS; ++i)
            if(crits[i]==nullptr) {
                crits[i] = new doodleBug(g);
                --doodleBugs;
            }
    }
}

void numberOfCrits(const grid& g, int& ants, int& doodleBugs) {
    for (int row = 0; row < ROW; ++row)
        for (int col = 0; col < COL; ++col) {
            if(g.get(row,col)=='o')
                ++ants;
            else if(g.get(row,col)=='x')
                ++doodleBugs;
        }
}

void initialize(organism** critters, grid& g) {
    for(int i=0; i<MAX_CRITTERS; ++i)
        critters[i] = nullptr;
    for(int i=0; i<5; ++i)
        critters[i] = new doodleBug(g);
    for(int i=5; i<105; ++i)
        critters[i] = new ant(g);
}

void deleteAll(organism** critters) {
    for(int i=0; i<MAX_CRITTERS; ++i)
        if(critters[i]!=nullptr)
            delete critters[i];
    grid::last = true;
    delete[] critters;
}
