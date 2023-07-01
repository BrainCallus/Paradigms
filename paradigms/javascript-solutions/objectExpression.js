"use strict";

class Operation {
    terms;
    oper;
    getValue;

    constructor(oper, getV, ...terms) {
        this.terms = terms[0];
        this.oper = oper;
        this.getValue = getV;
    }

    evaluate(x, y, z) {
        return this.getValue(
            this.terms.map(
                term => term.evaluate(x, y, z)));
    }

    toString() {
        return this.terms.map(term => term.toString()).join(" ") + " " + this.oper;
    }

    prefix() {
        return "(" + this.oper + " " + this.terms.map(term => term.prefix()).join(" ") + ")";
    }
}


class Variable {
    name;

    constructor(name) {
        this.name = name;
    }

    evaluate(...args) {
        return args[Vars.get(this.name)];
    }

    toString() {
        return this.name;
    }

    prefix() {
        return this.name;
    }
}

class Add extends Operation {
    constructor(first, second) {
        super("+", Operations.get("+")[1], [first, second]);
    }
}

class Subtract extends Operation {
    constructor(first, second) {
        super("-", Operations.get("-")[1], [first, second]);
    }
}

class Multiply extends Operation {
    constructor(first, second) {
        super("*", Operations.get("*")[1], [first, second]);
    }
}

class Divide extends Operation {
    constructor(first, second) {
        super("/", Operations.get("/")[1], [first, second]);
    }
}

class Negate extends Operation {
    constructor(term) {
        super("negate", Operations.get("negate")[1], [term]);
    }
}

class Exp extends Operation {
    constructor(term) {
        super("exp", Operations.get("exp")[1], [term]);
    }
}

class Ln extends Operation {
    constructor(term) {
        super("ln", Operations.get("ln")[1], [term]);
    }
}

class ArcTan extends Operation {
    constructor(term) {
        super("atan", Operations.get("atan")[1], [term]);
    }
}

class ArcTan2 extends Operation {
    constructor(term1, term2) {
        super("atan2", Operations.get("atan2")[1], [term1, term2]);
    }
}

class Sum extends Operation {
    constructor(...terms) {
        super("sum", Operations.get("sum")[1], terms);
    }
}

class Avg extends Operation {
    constructor(...terms) {
        super("avg", Operations.get("avg")[1], terms);
    }
}

class Const {
    constructor(val) {
        this.val = val;
    }

    evaluate(x, y, z) {
        return this.val;
    }

    toString() {
        return String(this.val);
    }

    prefix() {
        return String(this.val);
    }

}

class ParseError extends Error {
    constructor(message) {
        super(message);
    }
}

const Operations = new Map([
    ["+", [2, function (args) {
        return args[0] + args[1];
    }]],
    ["-", [2, function (args) {
        return args[0] - args[1];
    }]],
    ["*", [2, function (args) {
        return args[0] * args[1];
    }]],
    ["/", [2, function (args) {
        return args[0] / args[1];
    }]],
    ["negate", [1, function (args) {
        return -args[0];
    }]],
    ["exp", [1, function (args) {
        return Math.exp(args[0]);
    }]],
    ["ln", [1, function (args) {
        return Math.log(args[0]);
    }]],
    ["atan", [1, function (args) {
        return Math.atan(args[0]);
    }]],
    ["atan2", [2, function (args) {
        return Math.atan2(args[0], args[1]);
    }]],
    ["sum", [10, function (args) {
        let result = 0;
        for (let i = 0; i < args.length; i++) {
            result += args[i];
        }
        return result;
    }]],
    ["avg", [10, function (args) {
        return Operations.get("sum")[1](args) / args.length;
    }]]
]);

const Vars = new Map([["x", 0], ["y", 1], ["z", 2]]);

const parse = function (expression) {
    let parsed = [];
    let terms = expression.toString().split(" ").filter(a => a.length > 0);
    for (let i = 0; i < terms.length; i++) {
        if (Operations.get(terms[i]) !== undefined) {
            let pair = Operations.get(terms[i]);
            parsed[parsed.length - pair[0]] = new Operation(
                terms[i], pair[1], parsed.slice(parsed.length - pair[0], parsed.length));
            for (let j = 1; j < pair[0]; j++) {
                parsed.pop();
            }
            // лучше удалять элементы в цикле или parsed = parsed.slice(0, parsed.length-c[0]+1)?
        } else if (Vars.get(terms[i]) !== undefined) {
            parsed.push(new Variable(terms[i]))
        } else {
            parsed.push(new Const(parseInt(terms[i])));
        }
    }
    return parsed.pop();
}

class TermReader {
    ind;
    string;
    curTerm;

    constructor(string) {
        this.string = string.toString();
        this.ind = 0;
        this.curTerm = "";
    }

    getNext() {
        this.skipWhitespaces();
        this.curTerm = "";
        if (this.string[this.ind] === "(" || this.string[this.ind] === ")") {
            this.curTerm = this.string[this.ind++];
        } else {
            while (this.hasContinuation()) {
                this.curTerm += this.string[this.ind++];
            }
        }
    }

    getCurrent() {
        return this.curTerm;
    }

    hasContinuation() {
        return this.ind < this.string.length && this.string[this.ind] !== " " && this.string[this.ind] !== "("
            && this.string[this.ind] !== ")"
    }

    skipWhitespaces() {
        while (this.ind < this.string.length && this.string[this.ind] === " ") {
            this.ind++;
        }
    }
}

const parsePrefix = function (expression) {

    const parsePrimitive = function () {
        if (Vars.get(reader.getCurrent()) !== undefined) {
            return new Variable(reader.getCurrent());
        }
        if (!isNaN(reader.getCurrent())) {
            return new Const(parseFloat(reader.getCurrent()));
        }
        throw new ParseError("Unknown term " + reader.getCurrent());
    };
    const parseOperation = function () {
        reader.getNext();
        if (Operations.get(reader.getCurrent()) === undefined) {
            throw new ParseError("Operator expected, found " + reader.getCurrent());
        }
        let oper = reader.getCurrent();
        let params = Operations.get(oper);
        let terms = [];
        reader.getNext();
        while (reader.getCurrent() !== ")" && reader.getCurrent() !== "") {
            terms.push(parseAll());
            reader.getNext();
        }
        if (params[0] !== 10 && terms.length !== params[0]) {
            throw new ParseError("Operation " + oper + " applies " + params[0] + " operands");
        }
        return new Operation(oper, params[1], terms);
    };
    const parseAll = function () {
        if (Operations.get(reader.getCurrent()) !== undefined) {
            throw new ParseError("Unexpected operator " + reader.getCurrent());
        }
        if (reader.getCurrent() === "(") {
            let res = parseOperation();
            if (reader.getCurrent() !== ")") {
                throw new ParseError("Missed \")\"");
            }
            return res;
        }

        return parsePrimitive();
    };

    if (expression.length === 0) {
        throw new ParseError("Expression can't be empty");
    }
    const reader = new TermReader(expression);
    reader.getNext();
    let expr = parseAll();
    reader.getNext();
    if (reader.getCurrent() !== "") {
        throw new ParseError("Invalid expression, expected end of expression, found " + reader.getCurrent());
    }
    return expr;
};