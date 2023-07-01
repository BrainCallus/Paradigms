"use strict";

const execute = function (action) {
    return (...terms) => (...vars) => action(...terms.map(term => term(...vars)));
}
const cnst = numb => () => numb;
const one = cnst(1);
const two = cnst(2);
const variable = name => (...args) => args[Vars.get(name)];
const add = execute((x, y) => x + y);
const subtract = execute((x, y) => x - y);
const multiply = execute((x, y) => x * y);
const divide = execute((x, y) => x / y);
const negate = execute(x => -x);
const sin = execute(x => Math.sin(x));
const cos = execute(x => Math.cos(x));

const Constants = new Map([["1", one], ["2", two]]);
const Binary_Opers = new Map([["+", add], ["-", subtract], ["*", multiply], ["/", divide]]);
const Unary_Opers = new Map([["sin", sin], ["cos", cos],
    ["negate", negate]]);
const Vars = new Map([["x", 0], ["y", 1], ["z", 2]]);

const parse = function (expression) {
    let parsed = [];
    let terms = expression.toString().split(" ").filter(a=>a);
    for (let i = 0; i < terms.length; i++) {
        if ((terms[i] = terms[i].trim()).length === 0) {
            continue;
        }
        if (Vars.get(terms[i]) !== undefined) {
            parsed.push(variable(terms[i]))
        } else if (Binary_Opers.get(terms[i]) !== undefined) {

            parsed[parsed.length - 2] = Opers.get(terms[i])(parsed[parsed.length - 2], parsed[parsed.length - 1]);
            parsed.pop();

        } else if (Unary_Opers.get(term[i]) !== undefined) {
            parsed[parsed.length - 1] = Opers.get(terms[i])(parsed[parsed.length - 1]);
        } else if (Constants.get(terms[i]) !== undefined) {
            parsed.push(Constants.get(terms[i]));
        } else {
            parsed.push(cnst(parseFloat(terms[i])));
        }
    }
    return parsed[0];
}
//let express = parse("cnst(10)");
//console.log(express(1,1,2));

