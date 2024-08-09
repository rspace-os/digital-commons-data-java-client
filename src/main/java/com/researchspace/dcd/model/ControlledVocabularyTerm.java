package com.researchspace.dcd.model;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * When creating a new Deposition, subjects -- terms from a controlled
 * vocabulary -- can be included.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlledVocabularyTerm {
  private String term;
  private URI identifier;
  private String scheme;
}

