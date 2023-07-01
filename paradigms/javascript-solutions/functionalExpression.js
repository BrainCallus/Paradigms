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
const sinh = execute(x => Math.sinh(x));
const cosh = execute(x => Math.cosh(x));

const Constants = new Map([["1", one], ["2", two]]);
const Binary_Opers = new Map([["+", add], ["-", subtract], ["*", multiply], ["/", divide]]);
const Unary_Opers = new Map([["sin", sin], ["cos", cos], ["sinh", sinh], ["cosh", cosh],
    ["negate", negate]]);
const Vars = new Map([["x", 0], ["y", 1], ["z", 2]]);
