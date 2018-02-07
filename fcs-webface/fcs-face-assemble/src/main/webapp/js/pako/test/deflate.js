/*global describe, it*/


'use strict';


var zlib = require('zlib');

var pako    = require('../index');
var helpers = require('./helpers');
var testSamples = helpers.testSamples;
var assert  = require('assert');
var fs      = require('fs');
var path    = require('path');



var samples = helpers.loadSamples();


describe('Deflate defaults', function () {

  it('deflate, no options', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, {}, done);
  });

  it('deflate raw, no options', function (done) {
    testSamples(zlib.createDeflateRaw, pako.deflateRaw, samples, {}, done);
  });

  // OS_CODE can differ. Probably should add param to compare function
  // to ignore some buffer positions
  it('gzip, no options', function (done) {
    testSamples(zlib.createGzip, pako.gzip, samples, {}, done);
  });
});


describe('Deflate levels', function () {

  it('level 9', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 9 }, done);
  });
  it('level 8', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 8 }, done);
  });
  it('level 7', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 7 }, done);
  });
  it('level 6', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 6 }, done);
  });
  it('level 5', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 5 }, done);
  });
  it('level 4', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 4 }, done);
  });
  it('level 3', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 3 }, done);
  });
  it('level 2', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 2 }, done);
  });
  it('level 1', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 1 }, done);
  });
  it('level 0', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 0 }, done);
  });
  it('level -1 (implicit default)', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { level: 0 }, done);
  });
});


describe('Deflate windowBits', function () {

  it('windowBits 15', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 15 }, done);
  });
  it('windowBits 14', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 14 }, done);
  });
  it('windowBits 13', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 13 }, done);
  });
  it('windowBits 12', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 12 }, done);
  });
  it('windowBits 11', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 11 }, done);
  });
  it('windowBits 10', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 10 }, done);
  });
  it('windowBits 9', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 9 }, done);
  });
  it('windowBits 8', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { windowBits: 8 }, done);
  });
  it('windowBits -15 (implicit raw)', function (done) {
    testSamples(zlib.createDeflateRaw, pako.deflate, samples, { windowBits: -15 }, done);
  });

});


describe('Deflate memLevel', function () {

  it('memLevel 9', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 9 }, done);
  });
  it('memLevel 8', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 8 }, done);
  });
  it('memLevel 7', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 7 }, done);
  });
  it('memLevel 6', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 6 }, done);
  });
  it('memLevel 5', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 5 }, done);
  });
  it('memLevel 4', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 4 }, done);
  });
  it('memLevel 3', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 3 }, done);
  });
  it('memLevel 2', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 2 }, done);
  });
  it('memLevel 1', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { memLevel: 1 }, done);
  });

});


describe('Deflate strategy', function () {

  it('Z_DEFAULT_STRATEGY', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { strategy: 0 }, done);
  });
  it('Z_FILTERED', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { strategy: 1 }, done);
  });
  it('Z_HUFFMAN_ONLY', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { strategy: 2 }, done);
  });
  it('Z_RLE', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { strategy: 3 }, done);
  });
  it('Z_FIXED', function (done) {
    testSamples(zlib.createDeflate, pako.deflate, samples, { strategy: 4 }, done);
  });

});


describe('Deflate RAW', function () {
  // Since difference is only in rwapper, test for store/fast/slow methods are enougth
  it('level 4', function (done) {
    testSamples(zlib.createDeflateRaw, pako.deflateRaw, samples, { level: 4 }, done);
  });
  it('level 1', function (done) {
    testSamples(zlib.createDeflateRaw, pako.deflateRaw, samples, { level: 1 }, done);
  });
  it('level 0', function (done) {
    testSamples(zlib.createDeflateRaw, pako.deflateRaw, samples, { level: 0 }, done);
  });

});


describe('Deflate dictionary', function () {

  it('trivial dictionary', function (done) {
    var dict = new Buffer('abcdefghijklmnoprstuvwxyz');
    testSamples(zlib.createDeflate, pako.deflate, samples, { dictionary: dict }, done);
  });

  it('spdy dictionary', function (done) {
    var spdyDict = require('fs').readFileSync(require('path').join(__dirname, 'fixtures', 'spdy_dict.txt'));

    testSamples(zlib.createDeflate, pako.deflate, samples, { dictionary: spdyDict }, done);
  });

  it('handles multiple pushes', function () {
    var dict = new Buffer('abcd');
    var deflate = new pako.Deflate({ dictionary: dict });

    deflate.push(new Buffer('hello'), false);
    deflate.push(new Buffer('hello'), false);
    deflate.push(new Buffer(' world'), true);

    if (deflate.err) { throw new Error(deflate.err); }

    var uncompressed = pako.inflate(new Buffer(deflate.result), { dictionary: dict });

    if (!helpers.cmpBuf(new Buffer('hellohello world'), uncompressed)) {
      throw new Error('Result not equal for p -> z');
    }
  });
});


describe('Deflate issues', function () {

  it('#78', function () {
    var data = fs.readFileSync(path.join(__dirname, 'fixtures', 'issue_78.bin'));
    var deflatedPakoData = pako.deflate(data, { memLevel: 1 });
    var inflatedPakoData = pako.inflate(deflatedPakoData);

    assert.equal(data.length, inflatedPakoData.length);
  });
});
