const brand = {
    "id": 4,
    "name": "Brand-17"
}

const e_pole = {
    "id": 4,
    "brand": brand,
    "type": "PoleType-18",
    "description": null
}

const company = {
    "id": 4,
    "name": "Company-16",
    "date": "2020-12-28T00:43:32Z",
    "url": "https://google.de"
}

const company_e_pole = {
    "id": 4,
    "company": company,
    "base_price": 500.0,
    "epole": e_pole
}

const factor_type = {
    "id": 2,
    "name": "Type-Wahid",
    "description": null
};

const factor = {
    "id": 2,
    "type": factor_type,
    "name": "Factor-Wahid",
    "question": "Q?",
    "required": true,
    "description": "aa"
}

const factor_answer_boolean = {
    "id": 3,
    "factor": factor,
    "type": 6,
    "answer": true
}

const factor_answer_string = {
    "id": 3,
    "factor": factor,
    "type": 6,
    "answer": "string"
}

const factor_answer_number = {
    "id": 3,
    "factor": factor,
    "type": 6,
    "answer": [8.0, 9.0]
}

const factor_answer_double_number = {
    "id": 3,
    "factor": factor,
    "type": 6,
    "answer": [[8.0, 9.0], [8.0, 9.0]]
}

const factor_answer_price_boolean = {
    "id": 2,
    "price": 100.0,
    "factor_answer": factor_answer_boolean,
    "company_epole": company_e_pole
}

const factor_answer_price_string = {
    "id": 2,
    "price": 100.0,
    "factor_answer": factor_answer_string,
    "company_epole": company_e_pole
}

const factor_answer_price_number = {
    "id": 2,
    "price": 100.0,
    "factor_answer": factor_answer_number,
    "company_epole": company_e_pole
}

const factor_answer_price_double_number = {
    "id": 2,
    "price": 100.0,
    "factor_answer": factor_answer_double_number,
    "company_epole": company_e_pole
}

const question_boolean = {
    "id": 1,
    "question": "Question text...?",
    "required": false,
    "type": 1,
    "answers": [true, false],
    "description": "Description..,"
}

const question_string = {
    "id": 1,
    "question": "Question text...?",
    "required": false,
    "type": 2,
    "answers": ["test 1", "test 2"],
    "description": "Description..,"
}

const question_number = {
    "id": 1,
    "question": "Question text...?",
    "required": false,
    "type": 2,
    "answers": [],
    "description": "Description..,"
}

const question_double_number = {
    "id": 1,
    "question": "Question text...?",
    "required": false,
    "type": 4,
    "answers": [],
    "description": "Description..,"
}


function getQuestionResponse(type) {
    switch (parseInt(type)) {
        case 4:
            return question_double_number;
        case 3:
            return question_number;
        case 2:
            return question_string;
        case 1:
        default:
            return question_boolean;
    }
}

function getQuestion(type, answer_type) {
    switch (type) {
        default:
            return getQuestionResponse(answer_type);
    }
}

function getFactorTypeResponse() {
    return factor_type;
}

function getFactorType(type) {
    switch (type) {
        default:
            return getFactorTypeResponse();
    }
}

function getFactorResponse() {
    return factor;
}

function getFactor(type) {
    switch (type) {
        default:
            return getFactorResponse();
    }
}

function getFactorAnswerResponse(type) {
    switch (parseInt(type)) {
        case 4:
            return factor_answer_double_number;
        case 3:
            return factor_answer_number;
        case 2:
            return factor_answer_string;
        case 1:
        default:
            return factor_answer_boolean;
    }
}

function getFactorAnswer(type, answer_type) {
    switch (type) {
        default:
            return getFactorAnswerResponse(answer_type);
    }
}

function getFactorAnswerPriceResponse(type) {
    switch (parseInt(type)) {
        case 4:
            return factor_answer_price_double_number;
        case 3:
            return factor_answer_price_number;
        case 2:
            return factor_answer_price_string;
        case 1:
        default:
            return factor_answer_price_boolean;
    }
}

function getFactorAnswerPrice(type, answer_type) {
    switch (type) {
        default:
            return getFactorAnswerPriceResponse(answer_type);
    }
}

function findTerm(term, check) {
    if (term.includes(check)) return term;
}

function getObject(objectName, type, extra) {
    switch (objectName) {
        case findTerm(objectName, "Question"):
            return getQuestion(type, extra);
        case findTerm(objectName, "FactorAnswerPrice"):
            return getFactorAnswerPrice(type, extra);
        case findTerm(objectName, "FactorAnswer"):
            return getFactorAnswer(type, extra);
        case findTerm(objectName, "FactorType"):
            return getFactorType(type);
        case findTerm(objectName, "Factor"):
        default:
            return getFactor(type);
    }
}

function get(objectName, type, extra) {
    return JSON.stringify(getObject(objectName, type, extra));
}