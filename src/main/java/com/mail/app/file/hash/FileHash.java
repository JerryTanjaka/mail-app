package com.mail.app.file.hash;

import com.mail.app.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
