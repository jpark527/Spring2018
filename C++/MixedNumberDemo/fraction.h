#ifndef FRACTION_H
#define FRACTION_H

#include <iostream>
#include <sstream>
#include <cmath>

using namespace std;

class Fraction {

    int num, denom;
    int gcd(const int& n, const int& d) const;
    void simplify();
    void convert(const string& number);

protected:
    void set(int n, int d);

public:
    Fraction(int num=0, int denom=1);
    Fraction(string num);
    Fraction(double num);
    Fraction(const Fraction& copy);
    ~Fraction();
    Fraction& operator = (const Fraction& rhs);

    Fraction& operator += (const Fraction& rhs);
    Fraction& operator -= (const Fraction& rhs);
    Fraction& operator *= (const Fraction& rhs);
    Fraction& operator /= (const Fraction& rhs);

    const int& getNum() const;
    const int& getDenom() const;
    void display() const;
    double stod() const;
    int stoi() const;

    friend Fraction operator + (const Fraction& lhs, const Fraction& rhs);
    friend Fraction operator - (const Fraction& lhs, const Fraction& rhs);
    friend Fraction operator * (const Fraction& lhs, const Fraction& rhs);
    friend Fraction operator / (const Fraction& lhs, const Fraction& rhs);
    friend ostream& operator << (ostream& out, const Fraction& f);
    friend istream& operator >> (istream& in, Fraction& f);

    friend bool operator>(const Fraction& lhs, const Fraction& rhs);
    friend bool operator<(const Fraction& lhs, const Fraction& rhs);
    friend bool operator>=(const Fraction& lhs, const Fraction& rhs);
    friend bool operator<=(const Fraction& lhs, const Fraction& rhs);
    friend bool operator==(const Fraction& lhs, const Fraction& rhs);
    friend bool operator!=(const Fraction& lhs, const Fraction& rhs);
};

#endif // FRACTION_H
