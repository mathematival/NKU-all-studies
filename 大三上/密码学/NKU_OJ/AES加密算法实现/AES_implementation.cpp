﻿#include <iostream>
#include <iomanip>
#include <vector>
#include <sstream>
#include <string>

using namespace std;

class AES {
private:
    static const int Nb = 4; // 列数
    static const int Nk = 4; // 密钥中的字（32位）数
    static const int Nr = 10; // 轮数
    static const uint8_t sbox[256]; // S盒
    static const uint8_t Rcon[10]; // Rcon

    void KeyExpansion(const uint8_t* key, uint32_t* roundKeys); // 密钥扩展函数
    void SubBytes(uint8_t state[4][4]); // 字节替换函数
    void ShiftRows(uint8_t state[4][4]); // 行移位函数
    uint8_t xtime(uint8_t x); // 列混淆的乘法操作
    void MixColumns(uint8_t state[4][4]); // 列混淆函数
    void AddRoundKey(uint8_t state[4][4], uint32_t* roundKey); // 轮密钥加函数
public:
    void AES_encrypt(const uint8_t* plaintext, uint8_t* ciphertext, const uint8_t* key); // AES加密函数
    void hexStringToBytes(const string& hexStr, uint8_t* bytes); // 将字符串转换为uint8_t数组
};

// S盒
const uint8_t AES::sbox[256] = {
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
};

// Rcon
const uint8_t AES::Rcon[10] = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36 };

void AES::AES_encrypt(const uint8_t* plaintext, uint8_t* ciphertext, const uint8_t* key) {
    uint8_t state[4][4];
    uint32_t roundKeys[Nb * (Nr + 1)];
    KeyExpansion(key, roundKeys);

    // 将明文转换为状态矩阵
    for (int i = 0; i < 16; i++)
        state[i % 4][i / 4] = plaintext[i];

    // 添加初始轮密钥
    AddRoundKey(state, roundKeys);

    // 进行9轮加密
    for (int round = 1; round < Nr; round++) {
        SubBytes(state);
        ShiftRows(state);
        MixColumns(state);
        AddRoundKey(state, roundKeys + round * Nb);
    }

    // 最后一轮加密
    SubBytes(state);
    ShiftRows(state);
    AddRoundKey(state, roundKeys + Nr * Nb);

    // 将状态矩阵转换为密文
    for (int i = 0; i < 16; i++)
        ciphertext[i] = state[i % 4][i / 4];
}

void AES::KeyExpansion(const uint8_t* key, uint32_t* roundKeys) {
    uint32_t temp;
    int i = 0;

    while (i < Nk) {
        roundKeys[i] = (key[4 * i] << 24) | (key[4 * i + 1] << 16) | (key[4 * i + 2] << 8) | key[4 * i + 3];
        i++;
    }

    i = Nk;
    while (i < Nb * (Nr + 1)) {
        temp = roundKeys[i - 1];
        if (i % Nk == 0) {
            temp = (sbox[(temp >> 16) & 0xff] << 24) | (sbox[(temp >> 8) & 0xff] << 16) | (sbox[temp & 0xff] << 8) | sbox[(temp >> 24) & 0xff];
            temp ^= (Rcon[(i / Nk) - 1] << 24);
        }
        roundKeys[i] = roundKeys[i - Nk] ^ temp;
        i++;
    }
}

void AES::SubBytes(uint8_t state[4][4]) {
    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++)
            state[i][j] = sbox[state[i][j]];
}

void AES::ShiftRows(uint8_t state[4][4]) {
    uint8_t temp;

    // 第1行不变
    // 第2行循环左移1位
    temp = state[1][0];
    state[1][0] = state[1][1];
    state[1][1] = state[1][2];
    state[1][2] = state[1][3];
    state[1][3] = temp;

    // 第3行循环左移2位
    temp = state[2][0];
    state[2][0] = state[2][2];
    state[2][2] = temp;
    temp = state[2][1];
    state[2][1] = state[2][3];
    state[2][3] = temp;

    // 第4行循环左移3位
    temp = state[3][3];
    state[3][3] = state[3][2];
    state[3][2] = state[3][1];
    state[3][1] = state[3][0];
    state[3][0] = temp;
}

uint8_t AES::xtime(uint8_t x) {
    return (x << 1) ^ ((x & 0x80) ? 0x1b : 0x00);
}

void AES::MixColumns(uint8_t state[4][4]) {
    uint8_t tmp, tm, t;

    for (int i = 0; i < 4; i++) {
        t = state[0][i];
        tmp = state[0][i] ^ state[1][i] ^ state[2][i] ^ state[3][i];

        tm = state[0][i] ^ state[1][i];
        tm = xtime(tm);
        state[0][i] ^= tm ^ tmp;

        tm = state[1][i] ^ state[2][i];
        tm = xtime(tm);
        state[1][i] ^= tm ^ tmp;

        tm = state[2][i] ^ state[3][i];
        tm = xtime(tm);
        state[2][i] ^= tm ^ tmp;

        tm = state[3][i] ^ t;
        tm = xtime(tm);
        state[3][i] ^= tm ^ tmp;
    }
}

void AES::AddRoundKey(uint8_t state[4][4], uint32_t* roundKey) {
    for (int i = 0; i < 4; i++) {
        state[0][i] ^= (roundKey[i] >> 24) & 0xff;
        state[1][i] ^= (roundKey[i] >> 16) & 0xff;
        state[2][i] ^= (roundKey[i] >> 8) & 0xff;
        state[3][i] ^= (roundKey[i] & 0xff);
    }
}

void AES::hexStringToBytes(const string& hexStr, uint8_t* bytes) {
    for (int i = 0; i < hexStr.length(); i += 2) {
        string byteString = hexStr.substr(i, 2);
        bytes[i / 2] = stoul(byteString, nullptr, 16);
    }
}

int main() {
    string key_str, plaintext_str;
    cin >> key_str;
    cin >> plaintext_str;

    uint8_t key[16], plaintext[16], ciphertext[16];
    AES aes;
    aes.hexStringToBytes(key_str, key);
    aes.hexStringToBytes(plaintext_str, plaintext);
    aes.AES_encrypt(plaintext, ciphertext, key);

    for (int i = 0; i < 16; i++) {
        cout << hex << uppercase << setw(2) << setfill('0') << (int)ciphertext[i];
    }

    return 0;
}