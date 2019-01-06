#ifndef MixedNumber_H
#define MixedNumber_H

#include "fraction.h"

class MixedNumber : public Fraction {

        void copy(const MixedNumber& other);
        void copy(const Fraction& other);

    public:
        MixedNumber();
        MixedNumber(int w, int num = 0, int denom = 1);
        ~MixedNumber();
        MixedNumber(const MixedNumber &other);
        MixedNumber(const Fraction& other);
        MixedNumber& operator=(const MixedNumber &other);

        friend ostream& operator<<(ostream &out, const MixedNumber &m);
        friend istream& operator>>(istream &in, MixedNumber &m);
};

#endif // MixedNumber_H
